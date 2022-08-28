package input;

import base.*;

import java.util.Scanner;

public class Typer {
    private final Scanner scanner = new Scanner(System.in);

    public String typeName() {
        System.out.print("Введите имя города:" + "\n>");
        String name = Validator.validateName(scanner.nextLine());
        if (name == null) {
            System.out.println("Пустая строка не может быть именем.");
            return typeName();
        }
        return name;
    }

    public Coordinates typeCoordinates() {
        System.out.print("Введите координаты x и y через точку с запятой:" + "\n>");
        Coordinates coordinates = Validator.validateCoordinates(scanner.nextLine());
        if (coordinates == null) {
            System.out.println(
                    "Координат должно быть две. Обе координаты должны быть типа Double. Координата 'y' должна превышать -628.");
            return typeCoordinates();
        }
        return coordinates;
    }

    public Float typeArea() {
        System.out.print("Введите площадь города:" + "\n>");
        Float area = Validator.validateArea(scanner.nextLine());
        if (area == null) {
            System.out.println("Площадь города должна быть типа Float, её значение должно быть больше, чем 0.");
            return typeArea();
        }
        return area;
    }

    public int typePopulation() {
        System.out.print("Введите население города:" + "\n>");
        Integer population = Validator.validatePopulation(scanner.nextLine());
        if (population == null) {
            System.out.println("Население города должно быть типа Integer. Значение не должно быть меньше 0.");
            return typePopulation();
        }
        return population;
    }

    public float typeMetersAboveSeaLevel() {
        System.out.print("Введите высоту над уровнем моря:" + "\n>");
        Float masl = Validator.validateMetersAboveSeaLevel(scanner.nextLine());
        if (masl == null) {
            System.out.println("Высота над уровнем моря должна быть типа Float.");
            return typeMetersAboveSeaLevel();
        }
        return masl;
    }

    public Climate typeClimate() {
        System.out.println("Выберите климат (одну из предложенных цифр):");
        int i = 1;
        for (Climate clim : Climate.values()) {
            System.out.println(i++ + ") " + clim.toString());
        }
        System.out.print(">");
        Climate climate = Validator.validateClimateNum(scanner.nextLine());
        if (climate == null) {
            System.out.println("Выберите одну из предложенных цифр.");
            return typeClimate();
        }
        return climate;
    }

    public Government typeGovernment() {
        System.out.println("Выберите тип правления (одну из предложенных цифр):");
        int i = 1;
        for (Government gover : Government.values()) {
            System.out.println(i++ + ") " + gover.toString());
        }
        System.out.print(">");
        Government government = Validator.validateGovernmentNum(scanner.nextLine());
        if (government == null) {
            System.out.println("Выберите одну из предложенных цифр.");
            return typeGovernment();
        }
        return government;
    }

    public StandardOfLiving typeStandardOfLiving() {
        System.out.println("Выберите уровень жизни:");
        int i = 1;
        for (StandardOfLiving standard : StandardOfLiving.values()) {
            System.out.println(i++ + ") " + standard.toString());
        }
        System.out.print(">");
        StandardOfLiving standardOfLiving = Validator.validateStandardOfLivingNum(scanner.nextLine());
        if (standardOfLiving == null) {
            System.out.println("Выберите одну из предложенных цифр.");
            return typeStandardOfLiving();
        }
        return standardOfLiving;
    }

    public Human typeHuman() {
        System.out.println("Выберите правителя:");
        int i = 1;
        for (Leaders king : Leaders.values()) {
            System.out.println(i++ + ") " + king.toString());
        }
        System.out.print(">");
        Leaders leader = Validator.validateLeaderNum(scanner.nextLine());
        if (leader == null) {
            System.out.println("Выберите одну из предложенных цифр.");
            return typeHuman();
        }
        return Human.newHumanByLeader(leader);
    }

}
