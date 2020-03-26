package sargsyanarsen.task2;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadFromFileIntoArrayList {

    private ArrayList<User> users = new ArrayList<>();
    private File file;

    public ReadFromFileIntoArrayList(File file) {
        this.file = file;
    }

    public ReadFromFileIntoArrayList(String filePath) {
        file = new File(filePath);
    }

    public void go() {
        try {
            readFile();
            writeFile();
        } catch (Exception e) {
            System.out.println("Error during read/write: " + e.getMessage());
        }
    }

    private void readFile() throws IOException, ClassNotFoundException {
        if (!file.exists()) {
            boolean created = file.createNewFile();
            System.out.println(String.format("file created: %s at path: %s", created, file.getAbsoluteFile()));
        }
        try (BufferedReader bRid = new BufferedReader(new FileReader(file))) {
            String str;
            while ((str = bRid.readLine()) != null) {
                String[] userNameAndAge = str.split(" ");
                int age;
                int id;
                try {
                    age = Integer.parseInt(userNameAndAge[2]);
                    id = Integer.parseInt(userNameAndAge[0]);
                } catch (NumberFormatException e) {
                    System.out.println("User has not added: age or id parse exception: " + e.getMessage());
                    return;
                }
                users.add(new User(id, userNameAndAge[1], age));
            }
        }
    }

    private void writeFile() throws IOException {
        System.out.println(Constants.WELCOME_LOG_MESSAGE);
        Scanner scanner = new Scanner(System.in);
        String line;
        String[] array;
        Command command;
        do {
            System.out.println("\nInput command");
            line = scanner.nextLine();
            array = line.split(" ");
            int length = array.length;
            command = Command.valueOf(array[0].toUpperCase());
            switch (command) {
                case ADD: {
                    if (length == 4) {
                        int age;
                        int id;
                        try {
                            age = Integer.parseInt(array[3]);
                            id = Integer.parseInt(array[1]);
                        } catch (NumberFormatException e) {
                            System.out.println("age or id pars exceptions"+ e.getMessage());
                            break;
                        }
                        for (User user : users) {
                            if (user.getId() == id) {
                                System.out.println("with this id already exists user");
                                writeFile();
                            }
                        }
                        users.add(new User(id, array[2], age));
                        System.out.println("user added");
                    } else
                        System.out.println("wrong command");
                    break;
                }
                case REMOVE: {
                    if (length == 2) {
                        int index;
                        try {
                            index = Integer.parseInt(array[1]);
                        } catch (NumberFormatException e) {
                            System.out.println("index pars exceptions"+e.getMessage());
                            break;
                        }
                        if (users.size() < index) {
                            System.out.println("Index can not be small array.length");
                            break;
                        }
                        users.remove(index);
                        System.out.println("user removed");
                        break;
                    }
                    System.out.println("wrong command");
                    break;
                }
                case LIST: {
                    if (length == 1) {
                        for (User user : users) {
                            System.out.println(String.format("%d %s %d", user.getId(),user.getName(), user.getAge()));
                        }
                    } else System.out.println("wrong command");
                    break;
                }
                case HELP: {
                    if (length == 1) {
                        System.out.println(Constants.WELCOME_LOG_MESSAGE);
                    }else System.out.println("wrong command");
                    break;
                }
                case CLEAR: {
                    if (length == 1) {
                        users.clear();
                        file.delete();
                        System.out.println("users list has be clear");
                    }else System.out.println("wrong command");
                    break;
                }
                case EXIT: {
                    if (length == 1) {
                        try (BufferedWriter bWrit = new BufferedWriter(new FileWriter(file))) {
                            for (User user : users) {
                                bWrit.write(String.format("%d %s %d\n", user.getId(), user.getName(), user.getAge()));
                            }
                        }
                    }else System.out.println("wrong command");
                    break;
                }
                default: {
                    System.out.println("Input correct arguments:");
                    writeFile();
                }
            }
        } while (command != Command.EXIT);
    }
}
