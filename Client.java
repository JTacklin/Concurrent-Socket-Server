import java.net.*;
import java.io.*;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class Client {

    public class ClientThread extends Thread {

        // Constructor to pass arguments into the run
        private String command;
        private PrintWriter writer;
        private BufferedReader reader;

        public ClientThread(String initCommand, PrintWriter initWriter, BufferedReader initReader) {

            this.command = initCommand;
            this.writer = initWriter;
            this.reader = initReader;

        }

        // This is what the thread does on run
        public void run() {
            writer.println(command);
            String serverMsg;
            try {
                while (!(serverMsg = reader.readLine()).equals("ENDCMD")) { // while serverMsg is not empty keep
                                                                            // printing
                    System.out.println(serverMsg);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        if (args.length < 2)
            return; // minium length

        // arguments to use in command to connect i.e java ClientConnect port
        String hostname = args[0];
        int port = Integer.parseInt(args[1]);

        // runs the function
        Client cc = new Client();
        cc.start(hostname, port);
    }

    // Where the code actually runs
    private void start(String hostname, int port) {
        try (Socket socket = new Socket(hostname, port)) {

            // communicate w server
            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true); // sends data in text format, the true in autoflush
                                                                // clears data after each call //UNCECCESARY

            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        

            // do this while connection active
            while (true) {

                System.out.println(
                    "Command Menu\n----------------------\n1: Date and Time\n2: Uptime\n3: Memory Usage\n4: Netstat\n5: Current Users\n6: Running Processes\n7: Exit\n----------------------");
                System.out.print("Enter a menu number: ");

                Scanner scanner = new Scanner(System.in);
                String inputCmd = scanner.nextLine();
                String command;
                int threadCount;
                try {
                    switch (inputCmd) {
                        case "1":

                            command = "date";
                            System.out.println("How many threads do you want to make? (1,5,10,15,20,25,100): ");
                            threadCount = scanner.nextInt();
                            while (threadCount != 1 && threadCount != 5 && threadCount != 10 && threadCount != 15
                                    && threadCount != 20 && threadCount != 25 && threadCount != 100) {
                                System.out.println("Invalid amount of threads, try again (1,5,10,15,20,25,100): ");
                                threadCount = scanner.nextInt();
                            }
                            runCommand(writer, reader, command, threadCount);
                            continue;

                        case "2":

                            command = "uptime";
                            System.out.println("How many threads do you want to make? (1,5,10,15,20,25,100): ");
                            threadCount = scanner.nextInt();
                            while (threadCount != 1 && threadCount != 5 && threadCount != 10 && threadCount != 15
                                    && threadCount != 20 && threadCount != 25 && threadCount != 100) {
                                System.out.println("Invalid amount of threads, try again (1,5,10,15,20,25,100): ");
                                threadCount = scanner.nextInt();
                            }
                            runCommand(writer, reader, command, threadCount);
                            continue;

                        case "3":

                            command = "free";
                            System.out.println("How many threads do you want to make? (1,5,10,15,20,25,100): ");
                            threadCount = scanner.nextInt();
                            while (threadCount != 1 && threadCount != 5 && threadCount != 10 && threadCount != 15
                                    && threadCount != 20 && threadCount != 25 && threadCount != 100) {
                                System.out.println("Invalid amount of threads, try again (1,5,10,15,20,25,100): ");
                                threadCount = scanner.nextInt();
                            }
                            runCommand(writer, reader, command, threadCount);
                            continue;

                        case "4":

                            command = "netstat -all";
                            System.out.println("How many threads do you want to make? (1,5,10,15,20,25,100): ");
                            threadCount = scanner.nextInt();
                            while (threadCount != 1 && threadCount != 5 && threadCount != 10 && threadCount != 15
                                    && threadCount != 20 && threadCount != 25 && threadCount != 100) {
                                System.out.println("Invalid amount of threads, try again (1,5,10,15,20,25,100): ");
                                threadCount = scanner.nextInt();
                            }
                            runCommand(writer, reader, command, threadCount);
                            continue;

                        case "5":

                            command = "who";
                            System.out.println("How many threads do you want to make? (1,5,10,15,20,25,100): ");
                            threadCount = scanner.nextInt();
                            while (threadCount != 1 && threadCount != 5 && threadCount != 10 && threadCount != 15
                                    && threadCount != 20 && threadCount != 25 && threadCount != 100) {
                                System.out.println("Invalid amount of threads, try again (1,5,10,15,20,25,100): ");
                                threadCount = scanner.nextInt();
                            }
                            runCommand(writer, reader, command, threadCount);
                            continue;

                        case "6":

                            command = "ps -aux";
                            System.out.println("How many threads do you want to make? (1,5,10,15,20,25,100): ");
                            threadCount = scanner.nextInt();
                            while (threadCount != 1 && threadCount != 5 && threadCount != 10 && threadCount != 15
                                    && threadCount != 20 && threadCount != 25 && threadCount != 100) {
                                System.out.println("Invalid amount of threads, try again (1,5,10,15,20,25,100): ");
                                threadCount = scanner.nextInt();
                            }
                            runCommand(writer, reader, command, threadCount);
                            continue;

                        case "7":
                            System.out.println("Connection Terminated.\n");
                            socket.close();
                            scanner.close();
                            System.exit(0);

                        default:
                            System.out.println("Wrong input, try again!");
                            continue;

                    }

                }

                catch (InputMismatchException e) {

                    System.out.println("Invalid input, try again.");
                    continue;

                } 
                catch (NoSuchElementException e) {

                    System.out.println("Invalid input, try again.");
                    continue;
                }
                catch (IOException e) {
                    System.out.println("Invalid input, try again.");
                    continue;
                }

            }

            // catch errors
        } catch (UnknownHostException ex) {

            System.out.println("No host, no connection made.");

        } catch (IOException ex) {

            System.out.println("Encountered an exception, connection terminated.");

        }
    }

    private void runCommand(PrintWriter writer, BufferedReader reader, String userCmd, int counter) 
    {

        Thread t[] = new Thread[counter];
        long startTime = System.currentTimeMillis(); 
        for (int i = 0; i < counter; i++) {
            t[i] = new ClientThread(userCmd, writer, reader);
            System.out.println("Threads running: " + Thread.activeCount());
            t[i].start();
            }
        //wait till all the threads are done and then go to while loop
        for (int i = 0; i < counter; i++) 
        {
            try {
                t[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("Runtime in ms: " + totalTime);
        System.out.println("Average in ms: " + totalTime/counter);
            
    }
}
