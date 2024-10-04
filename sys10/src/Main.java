class Main {
    public static void main(String[] args) throws InterruptedException {
        thread яйцо = new thread("яйцо");
        thread chicken = new thread("курочка");

        яйцо.start();
        chicken.start();

        while (true) {
            if (!chicken.isAlive()){
                яйцо.join();
                System.out.println("яйцо");
                break;
            }

            else if (!яйцо.isAlive()) {
                chicken.join();
                System.out.println("курочка");
                break;
            }
        }
    }
}

class thread extends Thread {
    thread(String name) {
        setName(name);
    }

    @Override
    public void run() {
        try {
            Thread.sleep((int)(Math.random() * 1000L));
            System.out.println(getName());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}