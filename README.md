[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/tCLXqlpe)
# Tema2POO-2024
## Readme
## Rezolvare :

### \/\/ Clasa Airplane

### Variabile folosite :
#### - model, idFLight, leavingLocation, destination, urgent, format, desiredTime, concretTime, status -> retin date despre zbor :
#### --- modelul avionului folosit, ID-ul zborului, locatia de plecare, destinatie, un contor care verific daca zborul este urgent sau nu,
#### --- formatul timpului, timpul la care decoleaza / aterizeaza avionul, timpul concret
#### - Constructorul clasei primeste ca parametrii : 
#### --- modelul avionului, ID-ul zborului, locatia de plecare, destinatia si ora dorita
#### --- Daca locatia de plecare este Bucuresti, atunci avionul va avea statusul "WAITING_FOR_TAKEOFF", altfel primeste statusul "WAITING_FOR_LANDING"
#### - Am folosit getter-e si setter-e pentru a accesa / modifica atributele in timpul prelucrarii datelor (atributele sunt private si este singura metoda de a le accesa)
#### - Metoda toString() am suprascris-o astfel: 
    public String toString() {
        String desiredTimeFormated = getDesiredTime().format(DateTimeFormatter.ofPattern(format));

        if (getConcretTime() != null) {
            String concretTimeFormated = getConcretTime().format(DateTimeFormatter.ofPattern(format));

            return getModel() + " - " + getIdFlight() + " - " + getLeavingLocation() +
                    " - " + getDestination() + " - " + getStatus() + " - " + desiredTimeFormated +
                    " - " + concretTimeFormated;
        }

        return getModel() + " - " + getIdFlight() + " - " + getLeavingLocation() +
                " - " + getDestination() + " - " + getStatus() + " - " + desiredTimeFormated;
    }
### - Metoda va returna informatiile despre zbor in formatul de mai sus, iar daca am modificat atributul concretTime si afiseaza un timp la care avionul executa o actiune

### \/\/ Clasa NarrowBodyAirplane & WideBodyAirplane

#### Sunt clase care extind clasa Airplane. 
#### Constructorii acestor clase apeleaza constructorul clasei Airplane.
#### La apelul metodei toString(), se adauga tipul de avion.

### \/\/ Clasa Runway

### Atribute folosite :
#### - idRunway, airplaneList, runwayType, runwayComparator, runwayAirplaneType, runwayUnavailableTime, runwayIsAvailable
#### --- retin date despre ID-ul pistei, lista de avioane, tipul de pista (decolare/aterizare), comparator pentru constructia listei, tipul de avioane care apartin pistei, ora de la care pista devine accesibila si status-ul pistei
#### - Constructorul clasei primeste ca parametrii ID-ul pistei, tipul de avioane care se afla pe pista si tipul pistei
#### - Daca pista este de aterizare, comparatorul listei va compara avioanele in functie de : daca avionul are o urgenta, si apoi dupa ora dorita de aterizare
#### - Altfel, se vor compara avioanele in functie de ora de decolare
#### - Lista avioanelor este de tip PriorityQueue (de fiecare data cand apelez metoda poll(), voi extrage avionul care are timpul dorit cel mai mic);

### Metoda addAirplane() :
#### - Adauga un avion in lista de avioane de pe pista.
#### - Daca avionul pe care il adaugam nu corespunde cu tipul de pista unde va fi plasat, atunci se arunca exceptia IncorrectRunwayException

### Metoda runwayInUse() :
#### - metoda verifica mai intai daca se poate executa manevra; daca pista este ocupata, se va arunca exceptia UnavailableRunwayException
#### - se extrage primul avion din lista de avioane, care se afla in starea de asteptare
#### - la finalul executie metodei, se adauga avionul in lista de avioane, cu o prioritate mai mica decat avioanle care se afla in starea de asteptare

### Metoda toString() :
#### - afiseaza informatii despre pista : statusul ei si avioanele care se afla pe pista / vor ajunge pe pista (avioanele se afla in starea de asteptare);

### Folosesc getter-e si setter-e pentru a accesa atributele private din cadrul clasei Runway

### \/\/ Clasa Main

### Atribute folosite : 
#### - path, inputFile, exceptionFile, flightInfo, airport
#### - retin caile pentru fiecare fisier -> fisierul din care se citesc datele de intrare, fisierul in care se scriu erorile si un fisier unde se vor afisa informatiile despre fiecare avion
#### - airport este o lista de runways; este de tip HashMap (pot sa identific mai usor pista din care voi extrage datele / voi face modificari)

### Metoda Main() :
#### - folosesc un BufferReader pentru a citi datele din fisierul de intrare (Daca foloseam Scanner, la finalul fiecarii linii, imi concatena "\n" si apoi nu se mai executa corect codul)
#### - cat timp linia pe care o citesc contine date de intrare, apelez metoda handleCommands(), care se ocupa de fiecare comanda scrisa in fisierul de intrare
#### - verific ce comanda se apeleaza : "add_runway_in_use" / "allocate_plane" / "permission_for_maneuver" / "runway_info" / "flight_info" / "exit"

### handleAddRunway() :
#### - adaug o pista in lista aeroportului

### handleAllocateAirplane() :
#### - adaug un avion in pista unde a fost alocat si verific daca tipul avionului corespunde cu pista

### handlePermissionManeuver() :
#### - selectez pista de unde voi extrage primul avion care se afla in asteptare
#### - daca pista este ocupata, metoda va prinde exceptia si va scrie in fisierul de exceptii


### handleRunwayInfo() :
#### - se vor afisa informatiile despre pista selectata din aeroport

### handleFlightInfo() : 
#### - caut avionul din lista de piste despre care voi extrage informatiile si apoi le voi scrie in fisierul "flight_info"