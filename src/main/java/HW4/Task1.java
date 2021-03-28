package HW4;

public class Task1 {
    private char printedSymbol = 'C';
    private final Object loc = new Object();
    public static void main(String[] args) {
        Task1 task = new Task1();
        for (int i = 0; i < 3; i++) {
            new Thread(()->task.printSymbol('C','A')).start();
            new Thread(()->task.printSymbol('A','B')).start();
            new Thread(()->task.printSymbol('B','C')).start();
        }
    }

    private void printSymbol (char waitingSymbol, char newSymbol){
        try {
            synchronized (loc) {
                while (waitingSymbol != printedSymbol) {
                    loc.wait();
                }
                System.out.print(newSymbol);
                printedSymbol = newSymbol;
                loc.notify();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
