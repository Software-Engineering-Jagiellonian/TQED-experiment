package com.uj.atm.common;

import com.uj.atm.interfaces.IAccount;
import com.uj.atm.interfaces.ICreditCard;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;


/**
 * UWAGA:
 * 1. Nie wolno tworzyć publicznych pól, jedynie można używać metod z interface
 * 2. Nie wolno dopisywać własnych metod
 * 3. Nie wolno modyfikować interface
 * 4. Nie wolno zmieniać wersji javy ani junita.
 * 5. Nie wolno tworzyć nowych (własnych) konstruktorów. Można używać jedynie istniejących (bezparametrowych).
 */
public class CreditCard implements ICreditCard {

    private String pin;
    private IAccount account;

    public CreditCard() {
        this.pin = "0000";
        this.account = null;
    }

    @Override
    public void Init(String newPin, String newPinConfirm) {
        // Metoda init wymaga bycia zalogowanym do konta domyślną wartością pinu: "0000"
        if(newPin.equals(newPinConfirm) && newPin.length() == 4)
            this.pin = newPin;
    }

    @Override
    public boolean ChangePin(String oldPin, String newPin, String newPinConfirm) {
        if(this.pin.equals(oldPin) && newPin.equals(newPinConfirm)){
            this.pin = newPin;
            return true;
        }
        return false;
    }

    @Override
    public boolean IsPinValid(String pin) {
        if(this.pin != null && this.pin.equals(pin)){
            return true;
        }
        return false;
    }

    @Override
    public void AddAccount(IAccount account) {
        // Zakłada się, że konto przypisać do karty można tylko 1 raz bez możliwości późniejszej zmiany
        if(this.account == null)
            this.account = account;
    }

    @Override
    public IAccount GetAccount(){
        return this.account;
    }

    @Override
    public boolean DepositFunds(double amount) {
        //Sytuacja, w której wartość amount zostaje przekazana systemowi w sposób nieprawidłowy (ujemna wpłata) nie powinnna dokonać zmian w systemie. Jest zatem pomijana.
        if(this.account != null && amount > 0){
            this.account.DepositFunds(amount);
            return true;
        }
        return false;
    }

    @Override
    public boolean WithdrawFunds(double amount) {
        //Sytuacja, w której wartość amount zostaje przekazana systemowi w sposób nieprawidłowy (ujemna wypłata) nie powinnna dokonać zmian w systemie. Jest zatem pomijana.
        if(account != null && amount > 0 && account.AccountStatus() > amount){
            account.WithdrawFunds(amount);
            return true;
        }
        return false;
    }
}
