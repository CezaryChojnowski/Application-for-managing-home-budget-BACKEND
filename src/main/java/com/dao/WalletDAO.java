package com.dao;

import com.entity.Wallet;
import com.repository.UserRepository;
import com.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WalletDAO {

    private WalletRepository walletRepository;

    private UserRepository userRepository;

    @Autowired
    public WalletDAO(WalletRepository theWalletRepository, UserRepository theUserRepository){
        walletRepository = theWalletRepository;
        userRepository = theUserRepository;
    }

    public List<Wallet> findAllWallet(){
        return walletRepository.findAll();
    }

    public List<Wallet> findUserWallets(Integer id){
        return walletRepository.findUserWallets(id);
    }

    public Wallet findWalletByID(Integer walletID){
        return walletRepository.findWalletByID(walletID);
    }

    public Wallet createNewWallet(Wallet wallet, Integer userID){
        wallet.setName_wallet(wallet.getName_wallet());
        wallet.setBalance(wallet.getBalance());
        wallet.setUser(userRepository.findUserByID(userID));
        return walletRepository.save(wallet);
    }

    public boolean checkIfUserHasWalletWithTheGivenName(List<Wallet> userWallets, String newWalletName){
        return userWallets.stream().anyMatch(o -> o.getName_wallet().equals(newWalletName));
    }

    public Wallet findUsersWalletByID(Integer userID, Integer walletID){
        return walletRepository.findUsersWalletByID(userID, walletID);
    }

    public void removeWallet(Integer userID, Integer walletID){
        walletRepository.delete(findUsersWalletByID(userID, walletID));
    }

}
