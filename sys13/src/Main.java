import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class Account {
    private double balance;
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition alert = lock.newCondition();
    double amount;

    Account(double amount){
        this.amount = amount;
    }
    public void deposit(double amount) {
        lock.lock();
        try {
            balance += amount;
            System.out.println("Пополнено на: " + amount + ", Новый баланс: " + balance);
            alert.signalAll(); // Уведомляем ожидающие потоки о пополнении
        } finally {
            lock.unlock();
        }
    }

    public void withdraw(double amount) throws InterruptedException {
        lock.lock();
        try {
            while (balance < amount) {
                System.out.println("Недостаточно средств для снятия " + amount + ". Ожидание пополнения...");
                alert.await(); // Ждем, пока баланс не пополнится
            }
            balance -= amount;
            System.out.println("Снято: " + amount + ", Остаток на балансе: " + balance);
        } finally {
            lock.unlock();
        }
    }

    public double getBalance() {
        lock.lock();
        try {
            return balance;
        } finally {
            lock.unlock();
        }
    }
}

class Depositor implements Runnable {
    private final Account account;

    public Depositor(Account account) {
        this.account = account;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 1008657; i++) {
                account.deposit(account.amount);
                Thread.sleep(3); // Задержка между пополнениями
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("введите сумму, на которую пополнять: ");
        double summa = sc.nextDouble();

        System.out.println("введите сумму, которую снимать: ");
        double vichet = sc.nextDouble();

        Account account = new Account(summa);
        Thread depositorThread = new Thread(new Depositor(account));
        depositorThread.start();

        try {
            // Снимаем деньги в цикле
            for (int i = 0; i < 5; i++) {
                account.withdraw(vichet); // Попробуем снять 50
                Thread.sleep(3); // Задержка между снятиями
            }
            depositorThread.interrupt();
            System.out.println("Итоговый баланс: " + account.getBalance());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
