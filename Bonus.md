# Tema2POO - 2024
## Bonus
## Propunere noua functionalitate :
## Programarea perioadei de intretinere a avioanelor

### Odata ce un avion a aterizat pe o pista a aeroportului, acesta va fi trimis la service pentru mentenanța.
### Mentenanta va dura aproximativ 20 de minute, pana cand avionul se poate reintoarce pe pista pentru a-si continua călătoriile.
### Rutina de mentenanta se va apela de fiecare data cand avionul aterizeaza pe o pista, pentru a verifica daca avionul este sigur si nu s-au produs defecte in timpul zborului
### In aceasta perioada, avionul nu poate executa nicio manevra. Dupa ce perioada de mentenanta se termina, avionul devine valabil si poate executa manevra de decolare

## Pentru implementarea acestei functionalitati, propun urmatoarele schimbari :

    public class Airplane {
        ...
        private LocalTime maintenanceTime;

        private enum Status {
            WAITING_FOR_TAKEOFF,
            DEPARTED,
            WAITING_FOR_LANDING,
            LANDED,
            MAINTENANCE
        }
        ...
    }

## O clasa noua : Maintenance

    public class Maintenance {
        private HashMap<String, E> maintenanceList;

        public Maintenance() {
            maintenanceList = new HashMap<>();
        }

        public void scheduleMaintenance(Airplane airplane, LocalTime maintenanceTIme) {
            maintenanceList.put(airplane.getModel(), (E) airplane);
            airplane.setStatus(Airplane.Status.MAINTENANCE);
            airplane.setMaintenanceTime(maintenanceTime);
        }

        public E checkAirplaneForMaintenance(Airplane airplane, LocalTime currentTime) {
            if (currentTime.isAfter(airplane.getMaintenanceTime())) {
                maintenanceList.remove(airplane.getModel());
                airplane.setStatus(Airplane.Status.WAITING_FOR_TAKEOFF);
                return (E) airplane;
            }
            return null;
        }
    }