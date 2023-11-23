/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.visualjavafxapp;


import org.example.Clock_store;

public class BModel {
    static org.example.Clock_store m = new Clock_store();
    
    public static Clock_store build(){
        return m;
    }
}
