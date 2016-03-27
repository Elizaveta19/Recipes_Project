package com.acer.recipes;

import com.squareup.otto.Bus;

public final class BusStation {
    private static Bus bus = new Bus();

    public static Bus getBus(){
        return bus;
    }
}
