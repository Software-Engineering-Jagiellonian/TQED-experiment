package com.uj.atm.interfaces;

public interface ICreditCard {
    /**
     * Metoda zmienia PIN karty i zwraca true w przypadku poprawnej zmiany kodu PIN oraz false w przypadku nieudanej zmiany.
     * @param oldPin Stary PIN
     * @param newPin Nowy PIN
     * @param newPinConfirm Nowy PIN - wartość weryfikująca: newPin oraz newPinConfirm muszą być sobie równe.
     * @return Jeżeli uda się zmienić kod PIN, zwraca true. W przeciwnym razie zwraca false
     */
    boolean ChangePin(String oldPin, String newPin, String newPinConfirm);
    /**
     * Metoda sprawdza, czy PIN danej karty jest poprawny
     * @param pin Kod PIN zapisany jako łańcuch znakowy (String)
     * @return Jeżeli kod PIN jest poprawny zwraca true, w przeciwnym wypadku zwraca false.
     */
    boolean IsPinValid(String pin);
    /**
     * Metoda stowarzysza z daną kartą określone konto. Każda karta może być stowarzyszona z co najwyżej jednym kontem.
     * @param account Obiekt konta które będzie dodane do karty.
     */
    void AddAccount(IAccount account);
    /**
     * Metoda realizuje wpłatę pieniędzy na konto stowarzyszone z tą kartą
     * @param amount Kwota jaką chcemy wpłacić
     * @return Zwraca true lub false w zależności od tego,
     * czy operacja się uda (może się nie udać jeśli na przykład obiekt konta dla danej karty jest = null).
     */
    boolean DepositFunds(double amount);
    /**
     * Metoda realizuje wypłatę określonej kwoty z konta stowarzyszonego z daną kartą.
     * @param amount Kwota jaką chcemy wypłacić
     * @return Zwraca true lub false w zależności od tego, czy operacja się uda.
     */
    boolean WithdrawFunds(double amount);
}