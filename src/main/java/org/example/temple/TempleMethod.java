package org.example.temple;

public class TempleMethod {

    public final void print(String message){
        System.out.println("##########################################");
        wrapPrint(message);
        System.out.println("##########################################");
    }

    protected void wrapPrint(String message){

    }

    public static void main(String[] args) {
        TempleMethod templeMethod = new TempleMethod(){
            @Override
            protected void wrapPrint(String message) {
                System.out.println("*"+message+"*");
            }
        };
        templeMethod.print("3333333333333333333");
    }
}
