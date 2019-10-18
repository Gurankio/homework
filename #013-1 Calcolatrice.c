/*
 * Jacopo "Java" Del Granchio
 * #013 19.10.2019
 *
 * Calcolatrice con FOR.
 */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>

int main() {
  char scelta = 0;          // Scelta del menu.
  const int MAX_ERRORI = 3; // Massimo numero di errori.
  int a = 0, b = 0;         // Variabili delle operazioni.

  // Ripeto finchè non ho esaurito gli errori.
  for (int errori = MAX_ERRORI; errori != 0;) {
    // Stampo il menu.
    printf("Premi '#' per Uscire\n");
    printf("Premi '+' per Sommare\n");
    printf("Premi '-' per Sottrarre\n");
    printf("Premi '*' per Moltiplicare\n");
    printf("Premi '/' per Dividere\n");
    printf("Scelta: ");

    // Leggo la scelta.
    scanf(" %c", &scelta);

    // Se la scelta è diversa dal carattere che chiude il programma, chiedo due numeri.
    if (scelta == '+' || scelta == '-' || scelta == '*' || scelta == '/') {
      printf("Immettere il valore di A: ");
      scanf(" %d", &a);
      printf("Immettere il valore di B: ");
      scanf(" %d", &b);
    }

    // In base alle scelta eseguo:
    switch (scelta) {
      case '#':
        errori = 0; // Forzo gli errori a 0 per uscire dal loop.
        break;

      case '+':
        printf("Il risultato è %d\n\n", a + b);
        errori = MAX_ERRORI; // Riporto gli errori disponibili al massimo.
        break;

      case '-': {
        printf("Il risultato è %d\n\n", a - b);
        errori = MAX_ERRORI; // Riporto gli errori disponibili al massimo.
        break;
      }

      case '*':
        printf("Il risultato è %d\n\n", a * b);
        errori = MAX_ERRORI; // Riporto gli errori disponibili al massimo.
        break;

      case '/':

        // Controllo che il divisore sia diverso da 0.
        // Se b è 0 ne chiedo uno nuovo.
        while (b == 0) {
          printf("Impossibile dividere per 0.\n");
          printf("Immettere il nuovo valore di B: ");
          scanf(" %d", &b);
        }

        printf("Il risultato è %d con resto %d\n\n", a / b, a % b);
        errori = MAX_ERRORI; // Riporto gli errori disponibili al massimo.
        break;

      default: {
        errori--; // Se la scelta non è disponibile riduco gli errori rimantenti di uno.
        printf("Scelta '%c' non disponibile. ", scelta);
        printf("%d error%c rimanent%c.\n\n", errori, errori == 1 ? 'e' : 'i', errori == 1 ? 'e' : 'i');

        /*
         * L'espressione:
         *     errori == 1 ? 'e' : 'i'
         * corrisponde a:
         * if (errori == 1) {
         *   printf("%d error%c rimanent%c.\n", errori, 'e', 'e');
         * } else {
         *   printf("%d error%c rimanent%c.\n", errori, 'i', 'i');
         * }
         */

        break;
      }
    }
  }

  printf("Esco dal programma.\n");
  // system("PAUSE");
  return 0;
}
