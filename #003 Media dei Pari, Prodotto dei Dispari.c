/*
 * Jacopo Del Granchio
 * #003
<<<<<<< HEAD
 * Dati 5 numeri in input calcola la media dei numeri e il prodotto dei dispari.
=======
 *
 * Dati 5 numeri in input, fa la somma dei pari e il prodotto dei dispari.
>>>>>>> 9311976320145f8ba08dc75961e0a32628a1231d
 */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>
<<<<<<< HEAD

int t, size = 0, average = 0, product = 0;

int main() {
  for (int i = 0; i < 5; i++) {
    printf("Dammi un numero: ");
    scanf(" %d", &t);
=======

int main() {
  int t, size = 0, average = 0, product = 0;

  for (int i = 0; i < 5; ++i) {
    printf("Dammi un numero: ");
    scanf("%d", &t);
>>>>>>> 9311976320145f8ba08dc75961e0a32628a1231d

    if (t % 2 == 0) {
      average += t;
      size++;
    } else {
      if (product != 0) product *= t;
      else product += t;
    }
  }

  if (average != 0) printf("Media dei pari: %d\n", average / size);
  else printf("Nessun numero pari.\n");

  if (product != 0) printf("Prodotto dei dispari: %d\n", product);
  else printf("Nessun numero dispari.\n");

  getchar();
  return 0;
}
