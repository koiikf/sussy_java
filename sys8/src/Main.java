import java.util.Objects;

class RabbitAndTurtle {
    public static AnimalThread rabbit;
    public static AnimalThread turtle;

    public static void main(String[] args) {
        rabbit = new AnimalThread("Rabbit", Thread.MAX_PRIORITY);
        turtle = new AnimalThread("Turtle", Thread.NORM_PRIORITY);

        rabbit.start();
        turtle.start();
    }
}

class AnimalThread extends Thread {
    final int TRACE_DISTANCE = 100;
    int distance = 0;
    String name;
    int priority;
    boolean b = true;
    AnimalThread(String name, int priority) {
        this.name = name;
        this.priority = priority;
        setName(name);
        setPriority(priority);
    }

    @Override
    public void run() {
        while (distance < TRACE_DISTANCE) {

            try {
                // проверка на увелчение расстояния между черепашкой и кролем
                // если расстояние большое, меняем приоритет
                if (Math.abs(RabbitAndTurtle.turtle.getDistance() - RabbitAndTurtle.rabbit.getDistance()) >= 5 && b) {
                    int prior = RabbitAndTurtle.turtle.getPriority();
                    RabbitAndTurtle.turtle.setPriority(RabbitAndTurtle.rabbit.getPriority());
                    RabbitAndTurtle.rabbit.setPriority(prior);
                }
                // поток дрыхнет на время, в зависимости от приоритет
                Thread.sleep((int)(1000L / getPriority()));

                b = !b;

                distance += 1;

                String line = "";

                for (int i = 0; i < distance; i++) line += ".";
                if (Objects.equals(name, "Turtle")) line += "\uD80C\uDD89";
                else line += "\uD83D\uDC30";
                for (int i = distance; i < TRACE_DISTANCE; i++) line += ".";

                System.out.println(line);
                //System.out.println(getName() + ": " + distance);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private int getDistance() {
        return distance;
    }
}