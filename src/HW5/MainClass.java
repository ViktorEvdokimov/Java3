package HW5;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainClass {
    private static ExecutorService es;
    public static final int CARS_COUNT = 4;
    private static CountDownLatch waitinStart = new CountDownLatch(CARS_COUNT);
    private static Car[] cars;
    public static void main(String[] args) {
        es = Executors.newFixedThreadPool(CARS_COUNT);
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race(new Road(60), new Tunnel(80, CARS_COUNT/2), new Road(40));
        cars = new Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10));
        }
//        for (int i = 0; i < cars.length; i++) {
//            new Thread(cars[i]).start();
//        }
        for (Car car : cars){
            es.execute(()->start(car));
        }
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
        es.shutdown();
    }

    private static void start (Car car){
        car.preparation();
        waitinStart.countDown();
        try {
            waitinStart.await();
            car.run();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
