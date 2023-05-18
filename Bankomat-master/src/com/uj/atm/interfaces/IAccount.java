package com.uj.atm.interfaces;
public interface IAccount {
    /**
     * Zwraca bieżące saldo konta
     * Przykład: dla konta z saldem 384.34 metoda powinna zwrócić wartość 384.34
     * @return wartość liczbowa typu double określająca saldo konta
     */
    double AccountStatus();
    /**
     * Metoda realizuje wpłatę na konto
     * Po jej wykonaniu wartość środków na koncie powinna zwiększyć
     się o wartość amount
     * Przykład: jeśli na koncie jest 100 PLN to wykonanie DepositFunds(50)
     * powinno skutkować zwiększeniem środków na koncie na rzecz którego wykonano metodę o kwotę 50 PLN i końcowym saldem 150 PLN.
     * @param amount Dodatnia kwota do wpłacenia na konto.
     * @return Saldo konta po dokonaniu operacji wpłacenia na konto.
     */
    double DepositFunds(double amount);
    /**
     * Metoda realizuje wypłatę środków z konta.
     * Po jej wykonaniu wartość środków na koncie powinna zmniejszyć się o wartość amount
     * Przykład: dla konta z saldem 450 PLN wykonanie WithdrawFunds(50) dla konta,
     * na rzecz którego wykonano tę metodę powinna skutkować wypłatą 50 PLN i końcowym saldem 400 PLN.
     * @param amount Dodatnia kwota do wypłacenia z konta.
     * @return Saldo na koncie po wykonaniu operacji wypłaty.
     */
    double WithdrawFunds(double amount);
}