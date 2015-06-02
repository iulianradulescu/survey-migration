/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.digidata.esop.domain.enums;

/**
 *
 * @author iulian.radulescu
 */
public enum FillMode {

    TELEPHONE('C'),
    ONLINE('O'),
    OFFLINE('P'),
    ONLINE_PDF('U'),
    OFFLINE_PDF('S');

    private char mode;

    FillMode(char mode) {
        this.mode = mode;
    }
    
    public static FillMode valueOf( char mode ){
        for ( FillMode value : FillMode.values( ) ) {
            if (value.mode == mode ) {
                return value;
            }
        }
        
        //default is OFFLINE
        return FillMode.OFFLINE;
    }
}
