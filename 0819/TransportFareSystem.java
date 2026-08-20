abstract class Transport {
    private String routeName;

    public Transport(String routeName) {
        this.routeName = routeName;
    }

    public String getRouteName() {
        return routeName;
    }

    public abstract double calculateFare(int distance);
}

class Bus extends Transport {
    private double baseFare;

    public Bus(String routeName, double baseFare) {
        super(routeName);
        this.baseFare = baseFare;
    }

    @Override
    public double calculateFare(int distance) {
        if (distance <= 5) {
            return baseFare;
        }
        return baseFare + (distance - 5) * 2;
    }
}

class Taxi extends Transport {
    private double baseFare;

    public Taxi(String routeName, double baseFare) {
        super(routeName);
        this.baseFare = baseFare;
    }

    @Override
    public double calculateFare(int distance) {
        if (distance <= 2) {
            return baseFare;
        }
        return baseFare + (distance - 2) * 15;
    }
}

public class TransportFareSystem {
    public static void main(String[] args) {
        Transport[] transports = {
            new Bus("公車 307 路線", 15),
            new Bus("公車 299 路線", 15),
            new Taxi("計程車 - 市區專車", 85),
            new Taxi("計程車 - 機場接送", 85)
        };

        int distance = 10;

        for (Transport t : transports) {
            System.out.println("路線：" + t.getRouteName() + " | 行駛距離：" + distance + " km | 票價：" + t.calculateFare(distance) + " 元");
        }
    }
}