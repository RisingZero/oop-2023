Sports
======

Il programma implementa il back-end di un portale di valutazioni di attrezzature sportive.

Tutte le classi si trovano nel package **sports**. La classe principale � **Sports**. La classe **TestApp** nel package **example** contiene un esempio. Le eccezioni sono lanciate mediante la classe **SportsException**.

È possibile accedere alla [documentazione delle API Java](https://oop.polito.it/api).

R1 Attivit� e Categorie
-----------------------

Il metodo **defineActivities(String... activities)** definisce le attivit� sportive trattate dal portale. Il metodo lancia un'eccezione se non � passata alcuna attivit�.

Il metodo **getActivities()** d� la lista delle attivit� definite, ordinate alfabeticamente.

Il metodo **addCategory(String name, String... linkedActivities)** aggiunge una categoria di prodotti e le attivit� collegate ad essi. Lancia un'eccezione se qualcuna delle attivit� non � definita.

Il metodo **countCategories()** restituisce il numero di categorie definite.

Il metodo **getCategoriesForActivity(String activity)** produce la lista ordinata alfabeticamente dei nomi delle categorie legate all'attivit� sportiva indicata. D� una lista vuota se non � definita nessuna categoria per l'attivit�, o se l'attivit� non esiste.

R2 Prodotti
-----------

Il metodo **addProduct(String name, String activity, String category)** aggiunge un prodotto di cui sono dati il nome, l'attivit� e la categoria di appartenenza. Lancia un'eccezione se esiste gi� un prodotto con quel nome.

Il metodo **getProductsForCategory(String categoryName)** restituisce la lista ordinata alfabeticamente dei nomi dei prodotti della categoria indicata. Se la categoria non � definita o non ha prodotti viene restituita una lista vuota.

Il metodo **getProductsForActivity(String activityName)** restituisce la lista ordinata alfabeticamente dei nomi dei prodotti dell'attivit� indicata. Se l'attivit� non � definita o non ha prodotti viene restituita una lista vuota.

Il metodo **getProducts(String activityName, String... categoryNames)** restituisce la lista ordinata dei nomi dei prodotti che appartengono all'attivit� indicata e ad una delle categorie indicate. Se l'attivit� non � definita o non ha prodotti viene restituita una lista vuota.

R3 Utenti e recensioni
----------------------

Il metodo **addRating(String productName, String userName, int numStars, String comment)** aggiunge una recensione per un prodotto da parte di un utente del sistema. La recensione prevede un voto, come numero di stelle tra 0 e 5, ed un commento. Lancia un'eccezione se il numero di stelle non � compreso tra 0 e 5 (inclusi).

Il metodo **getRatingsForProduct(String productName)** produce la lista ordinata per numero di stelle decrescente delle recensioni formattate come _"# : \[Comment\]"_, dove _#_ � il numero di stelle mentre _\[Comment\]_ � il commento associato. D� una lista vuota se nessun utente ha inserito recensioni per il prodotto.

R4 Valutazioni
--------------

Il metodo **getStarsOfProduct(String productName)** d� il numero medio di stelle delle recensioni del prodotto indicato.

Il metodo **averageStars()** calcola il numero medio di stelle su tutte le recensioni.

R5 Statistiche
--------------

Il metodo **starsPerActivity()** d� una mappa che associa il nome di una attivit� alla media di stelle dei prodotti ad essa associati, con i nomi delle attivit� ordinati alfabeticamente. Nel risultato non devono comparire le attivit� i cui prodotti non hanno ricevuto recensioni.

Il metodo **getProductsPerStars** d� una mappa che associa al numero medio di stelle la lista dei nomi di prodotti che hanno tale media, con le medie ordinate in modo decrescente e i nomi dei prodotti ordinati alfabeticamente. Sono scartati i prodotti senza recensioni.