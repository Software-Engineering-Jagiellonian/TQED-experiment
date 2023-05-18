package com.uj.atm.Test;

import com.uj.atm.common.DummySample;
import com.uj.atm.interfaces.IDummySample;
import org.junit.Assert;
import org.junit.Test;

public class DummyTest {

    /**
     * Podstawowy test. Dla każdego testu sprawdzamy wartość asercji poprzez == oraz equals ze względu na różnice
     * w wartościach podczas porównywania wartości na stosie i na stercie (obiekty referencyjne i niereferencyjne).
     * Dokładniejsze informacnie: https://www.geeksforgeeks.org/difference-equals-method-java/
     */
    @Test
    public void test01(){
        IDummySample dummyNWD = new DummySample();
        Assert.assertTrue(dummyNWD.NWD(15,876) == 3);
        Long l1 = dummyNWD.NWD(15,876);
        Long l2 = 3L;
        Assert.assertTrue(l1.equals(l2));
    }

    /**
     * Test sprawdzający zero jako jeden z parametrów dla NWD
     */
    @Test
    public void test02(){
        IDummySample dummyNWD = new DummySample();
        Assert.assertTrue(dummyNWD.NWD(0,-42) == 0);
        Long l1 = dummyNWD.NWD(0,-42);
        Long l2 = 0L;
        Assert.assertTrue(l1.equals(l2));
    }

    /**
     * Test sprawdzający equals kontra '==' dla części zmiennych numerycznych niereferencyjnych.
     * W javie do 128 (2^7) adres w pamięci jest równy wartości.
     */
    @Test
    public void test03(){
        IDummySample dummyNWD = new DummySample();
        Assert.assertTrue(dummyNWD.NWD(12547,1563130354) == 12547);
        Long l1 = dummyNWD.NWD(12547,1563130354);
        Long l2 = 12547L;
        Assert.assertTrue(l1.equals(l2));
    }
}
