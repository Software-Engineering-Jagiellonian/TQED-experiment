package com.uj.atm.common;

import com.uj.atm.interfaces.IDummySample;

public class DummySample implements IDummySample {

    @Override
    public long NWD(long a, long b) {
        if(a == 0 || b == 0 )
        {
            return 0;
        }

        while (a != b) // dopóki dwie liczby nie są sobie równe
        {
            if (a > b)  // sprawdzamy, która z nich jest większa
            {
                a = a - b; // odejmujemy mniejszą liczbę od większej
            }
            else
            {
                b = b - a;
            }
        }
        return a;
    }

}
