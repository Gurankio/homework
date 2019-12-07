/*
 * Jacopo Del Granchio
 * #064 11.12.2019
 *
 * Gioco del numero magico.
 */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <locale.h>
#include <stdarg.h>
#include <stdbool.h>
#include <time.h>

// Macro
#define chiedi(msg, format, ...) \
  printf(msg); \
  scanf(format, __VA_ARGS__);

// Prototipi
void printMenu();
void gioca(int);

// Funzioni
int main() {
  setlocale(LC_ALL, "");

  int n, err = 5;
  do {
    printMenu();
    chiedi("Scelta: ", "%d", &n);
    switch (n) {
      case 1:
        chiedi("Inserisci il numero di tentativi: ", "%d", &err);
        break;

      case 2:
        gioca(err);
        break;

      case 3:
        printf("Arrivederci.\n");
        break;

      default:
        printf("Inserire un numero tra 1 e 3 compresi.\n");
        break;
    }
  } while (n != 3);


  // getchar();
  // system("pause");
  return 0;
}

void printMenu() {
  printf("1) Scegli il numero di tentativi\n");
  printf("2) Gioca una partita\n");
  printf("3) Esci\n");
}

void gioca(int err) {
  srand((unsigned)time(NULL));
  int numA, numB, numC, numD;
  int guessA, guessB, guessC, guessD;
  int t, ok, vinto = 0;

  numA = rand() % 10;

  do numB = rand() % 10;
  while (numB == numA);

  do numC = rand() % 10;
  while (numC == numA || numC == numB);

  do numD = rand() % 10;
  while (numD == numA || numD == numB || numD == numC);

  // DEBUG:
  // printf("%d%d%d%d\n", numA, numB, numC, numD);

  do {
    do {
      chiedi("Inserire un numero di 4 cifre diverse tra loro: ", "%d", &t);

      guessA = t / 1000;
      guessB = t / 100 % 10;
      guessC = t / 10 % 10;
      guessD = t % 10;

      ok = guessA != guessB && guessA != guessC && guessA != guessD && guessB != guessC && guessB != guessD && guessC != guessD;

      if (ok == 0) printf("Il numero inserito deve essere di 4 cifre diverse tra loro.\n");
    } while (ok == 0);

    int giuste = 0, sbagliate = 0;

    giuste += (guessA == numA) + (guessB == numB) + (guessC == numC) + (guessD == numD);

    sbagliate += (guessA == numB) || (guessA == numC) || (guessA == numD);
    sbagliate += (guessB == numA) || (guessB == numC) || (guessB == numD);
    sbagliate += (guessC == numA) || (guessC == numB) || (guessC == numD);
    sbagliate += (guessD == numA) || (guessD == numB) || (guessD == numC);

    if (giuste == 4) vinto = 1;
    else {
      printf("%s %d cifr%c giust%c nel posto giusto.\n", (giuste == 1 ? "C'è" : "Ci sono"), giuste, (giuste == 1 ? 'a' : 'e'), (giuste == 1 ? 'a' : 'e'));
      printf("%s %d cifr%c giust%c nel posto sbagliato.\n", (sbagliate == 1 ? "C'è" : "Ci sono"), sbagliate, (sbagliate == 1 ? 'a' : 'e'), (sbagliate == 1 ? 'a' : 'e'));
    }

    err--;
  } while (err > 0 && vinto == 0);

  if (vinto) printf("Hai vinto.\n");
  else printf("Hai perso. Il numero era %d%d%d%d\n", numA, numB, numC, numD);
}
