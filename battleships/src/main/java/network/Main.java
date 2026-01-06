package network;

public class Main {
    public static void main(String[] args) {
        String mode = null;
        int port = 0;
        String mapFile = null;
        String host = null;


        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-mode":
                    mode = args[++i];
                    break;
                case "-port":
                    port = Integer.parseInt(args[++i]);
                    break;
                case "-map":
                    mapFile = args[++i];
                    break;
                case "-host":
                    host = args[++i];
                    break;
                default:
                    System.out.println("Nieznany argument: " + args[i]);
                    System.exit(1);
            }
        }

        if (mode == null || port == 0 || mapFile == null) {

            System.out.println("-mode [server|client] -port N -map map-file [-host hostName]");
            return;
        }

        switch (mode) {
            case "server":
                Server server = new Server(port, mapFile);
                server.run();
                break;
            case "client":
                if (host == null) {
                    System.out.println("podaj -host");
                    return;
                }
                Client client = new Client(host, port, mapFile);
                client.run();
                break;
            default:
                System.out.println("zly tryb");
        }
    }
}
