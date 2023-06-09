Gestione Foglio Ore
===================

Si realizzi un’applicazione per gestire i fogli ore di un’azienda.

Tutte le classi si trovano nel package `timesheet` . La classe principale è `Timesheet` . La classe `TestApp` nel package `example` contiene un esempio. Le eccezioni sono lanciate mediante la classe `TimesheetException` .

È possibile accedere alla [documentazione delle API Java](https://oop.polito.it/api) .

R1 Calendario
-------------

Il metodo `setHolidays(int... holidays)` permette di impostare i giorni festivi dell’anno. I giorni festivi sono rappresentati da un insieme di numeri interi compresi tra 1 e 365, forniti come parametro del metodo. Ogni data è il numero del giorno all’interno dell’anno (per esempio il primo gennaio è _1_ , il 31 dicembre è _365_ ). Si supponga che l’anno non sia mai bisestile. I giorni associati a numeri minori o uguali a _0_ oppure ripetuti devono essere ignorati. Ulteriori chiamate allo stesso metodo non producono alcun effetto.

Il metodo `isHoliday(int day)` ritorna un valore booleano che sarà _true_ se il giorno passato come parametro è festivo, _false_ in tutti gli altri casi.

Il metodo `setFirstWeekDay(int weekDay)` riceve come parametro un numero intero che rappresenta il giorno della settimana del primo gennaio. Il numero _0_ rappresenta una domenica, il numero _1_ un lunedì ecc. Se il numero fornito è minore di _0_ o maggiore di _6_ viene lanciata l’eccezione `TimesheetException` . Se il metodo viene chiamato più volte il nuovo valore sovrascrive il precedente.

Il metodo `getWeekDay(int day)` ritorna il numero del giorno della settimana dato il numero del giorno dell’anno. Per esempio, se il primo giorno dell’anno è un venerdì, al giorno _2_ corrisponde un sabato, ovvero il giorno della settimana _6_ . Se il numero del giorno fornito è minore o uguale a _0_ viene lanciata l’eccezione `TimesheetException` . Se il giorno della settimana del primo gennaio non è definito, si assuma sia un lunedì.

R2 Progetti
-----------

Il metodo `createProject(String projectName, int maxHours)` crea un nuovo progetto a cui sono associate un numero massimo di ore che possono essere dedicate ad esso. Si supponga che i nomi dei progetti siano univoci. Se il numero di ore è negativo viene lanciata l’eccezione `TimesheetException` . È possibile modificare il numero massimo di ore chiamando più volte il metodo per lo stesso progetto.

Il metodo `getProjects()` ritorna la lista dei progetti disponibili ordinati in modo decrescente per numero massimo di ore e quindi alfabeticamente. Il formato da utilizzare nella lista è: `"{projectName}: {maxHours}"` .

Il metodo `createActivity(String projectName, String activityName)` crea una nuova attività associata ad un certo progetto. Si supponga che i nomi delle attività siano univoci all’interno del progetto. Se il progetto non è definito viene lanciata l’eccezione `TimesheetException` .

Il metodo `closeActivity(String projectName, String activityName)` imposta come completata l’attività associata ad un certo progetto. Inizialmente tutte le attività sono non completate. Se il progetto o l’attività non sono definiti viene lanciata l’eccezione `TimesheetException` .

Il metodo `getOpenActivities(String projectName)` ritorna la lista delle attività non completate associate ad un certo progetto, ordinata alfabeticamente. Se il progetto non è definito viene lanciata l’eccezione `TimesheetException` .

R3 Lavoratori
-------------

Il metodo `createProfile(int... workHours)` definisce un nuovo profilo orario, ovvero il numero massimo di ore che un lavoratore può effettuare per giorno della settimana. Il vettore di numeri interi deve contenere esattamente sette valori, dove il primo si riferisce alla domenica e l’ultimo al sabato. Per esempio, se il vettore è _\[0, 8, 8, 8, 8, 8, 0\]_ il lavoratore può svolgere al massimo otto ore al giorno dal lunedì al venerdì. Se il vettore non contiene sette valori viene lanciata l’eccezione `TimesheetException` . Il metodo ritorna un identificativo univoco associato al profilo orario generato casualmente dal sistema.

Il metodo `getProfile(String profileID)` ritorna un profilo orario dato il suo identificativo. Se l’identificativo non è definito viene lanciata l’eccezione `TimesheetException` . Si utilizzi un formato simile al seguente: “Sun: 0; Mon: 8; Tue: 8; Wed: 8; Thu: 8; Fri: 8; Sat: 0”.

Il metodo `createWorker(String name, String surname, String profileID)` aggiunge un nuovo lavoratore al sistema e ritorna un identificativo univoco associato al lavoratore generato casualmente dal sistema. Se l’identificativo del profilo orario non è valido viene lanciata l’eccezione `TimesheetException` .

Il metodo `getWorker(String workerID)` ritorna una stringa nel formato `"{name} {surname} ({profileString})"` , dove `"{profileString}"` è il formato definito da `getWorkerProfile(String profileID)` , dato l’identificativo del lavoratore. Se l’identificativo del lavoratore non è valido viene lanciata l’eccezione `TimesheetException` .

**Suggerimento:** il metodo per generare gli identificativi univoci è lasciato alla scelta dello sviluppatore, purché garantisca l’unicità per ogni istanza della classe Timesheet.

R4 Rendicontazione
------------------

Il metodo `addReport(String workerID, String projectName, String activityName, int day, int workedHours)` aggiunge una nuova voce al foglio ore del lavoratore. È necessario verificare che le seguenti condizioni siano tutte soddisfatte, altrimenti viene lanciata l’eccezione `TimesheetException` :

*   l’identificativo del lavoratore sia valido;
*   il giorno sia maggiore di _0_ e non sia un festivo;
*   il numero di ore specificato non sia negativo;
*   il numero di ore sia compatibile con il profilo orario del lavoratore;
*   il progetto e l’attività siano definiti;
*   le ore totali del progetto non superino il valore massimo ammesso;
*   l’attività non sia già stata completata.

Il metodo `getProjectHours(String projectName)` ritorna il numero di ore lavorate associate ad un certo progetto. Se il progetto non è definito viene lanciata l’eccezione `TimesheetException` .

Il metodo `getWorkedHoursPerDay(String workerID, int day)` ritorna il numero di ore lavorate dato l’identificativo del lavoratore ed il giorno di lavoro. Se l’identificativo del lavoratore non è valido oppure il numero del giorno è minore o uguale a _0_ viene lanciata l’eccezione `TimesheetException` .

R5 Statistiche
--------------

Il metodo `countActivitiesPerWorker()` ritorna una mappa che associa ad ogni lavoratore il numero totale di attività distinte svolte, ovvero quelle attività per cui vi è almeno un’ora di rendicontazione. Si ricorda che le attività sono univoche all’interno di ogni progetto, quindi eventuali attività con lo stesso nome ma appartenenti a progetti diversi devono essere considerate distinte.

Il metodo `getRemainingHoursPerProject()` ritorna una mappa che associa ogni progetto al numero di ore ancora disponibili, ovvero il numero massimo di ore meno le ore giè rendicontate.

Il metodo `getHoursPerActivityPerProject()` ritorna una mappa che associa ad ogni progetto una mappa che associa ogni attività del progetto al numero totale di ore rendicontate.