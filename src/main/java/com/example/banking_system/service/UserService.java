package com.example.banking_system.service;

import com.example.banking_system.DTO.DepositDTO;
import com.example.banking_system.DTO.UserLoginDTO;
import com.example.banking_system.DTO.WithdrawDTO;
import com.example.banking_system.modal.Card;
import com.example.banking_system.modal.Users;
import com.example.banking_system.repository.CardRepository;
import com.example.banking_system.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CardRepository cardRepository;

    public String login(UserLoginDTO userLoginDTO) throws Exception {
        Optional<Users> users = userRepository.findByUsername(userLoginDTO.getUsername());
        if (users.isEmpty()) throw new EntityNotFoundException("No user found with this username!");
        if (!users.get().getPassword().equals(userLoginDTO.getPassword())) throw new Exception("Password mismatch!");
        return "Login Successful!";
    }

    public Long getBalance(String cardNum){
        Optional<Card> card = cardRepository.findByCardNum(cardNum);
        if (card.isEmpty()){
            throw new EntityNotFoundException("No card found with this number!");
        }

        return card.get().getAmount();
    }

    @Transactional
    public String deposit(DepositDTO depositDTO){
        Optional<Card> cardSender = cardRepository.findByCardNum(depositDTO.getSender());
        if (cardSender.isEmpty()) throw new EntityNotFoundException("Error in sender card number");
        Optional<Card> cardReceiver = cardRepository.findByCardNum(depositDTO.getReceiver());
        if (cardReceiver.isEmpty()) throw new EntityNotFoundException("Error in receiver card number");

        cardSender.get().setAmount(cardSender.get().getAmount()-depositDTO.getAmount());
        cardReceiver.get().setAmount(cardReceiver.get().getAmount()+depositDTO.getAmount());

        cardRepository.save(cardSender.get());
        cardRepository.save(cardReceiver.get());

        return "Successful deposit!";
    }

    @Transactional
    public String withdraw(WithdrawDTO withdrawDTO) throws Exception {
        Optional<Card> ownerCard = cardRepository.findByCardNum(withdrawDTO.getSender());
        if (ownerCard.isEmpty()) throw new EntityNotFoundException("Error in owner card number");

        if (!ownerCard.get().getPassword().equals(withdrawDTO.getPassword())) throw new Exception("Password mismatch!");

        if (withdrawDTO.getAmount()>ownerCard.get().getAmount()) throw new EntityNotFoundException("No sufficient money to proceed the process!");

        ownerCard.get().setAmount(ownerCard.get().getAmount()-withdrawDTO.getAmount());

        return "Successful withdrawal!";
    }

    public List<String> fetchCardNums(String username){
          Optional<Users> user = userRepository.findByUsername(username);
          List<Card> cards = cardRepository.findAll();
          List<String> list = new ArrayList<>();

        for (Card card:cards) {
            if (card.getUsers()==user.get()){
                list.add(card.getCardNum());
            }
        }

        return list;
    }


}
