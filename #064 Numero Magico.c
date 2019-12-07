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

  int n, max = 100;
  do {
    printMenu();
    chiedi("Scelta: ", "%d", &n);
    switch (n) {
      case 1:
        chiedi("Inserisci il numero massimo: ", "%d", &max);
        break;

      case 2:
        gioca(max);
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
  printf("1) Scegli numero massimo\n");
  printf("2) Gioca una partita\n");
  printf("3) Esci\n");
}

void gioca(const int MAX) {
  srand((unsigned)time(NULL));
  int num = rand() % MAX;
  int t, err = 5, vinto = 0;

  do {
    chiedi("Inserisci un numero: ", "%d", &t);

    if (t == num) vinto = 1;

    if (t < num) printf("Il numero è troppo basso.\n");

    if (t > num) printf("Il numero è troppo alto.\n");

    err--;
  } while (err > 0 && vinto == 0);

  if (vinto) printf("Hai vinto.\n");
  else printf("Hai perso. Il numero era %d\n", num);
}
