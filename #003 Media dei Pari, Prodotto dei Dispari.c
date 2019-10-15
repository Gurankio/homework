/*
 * Jacopo Del Granchio
 * #003
 * Dati 5 numeri in input calcola la media dei numeri e il prodotto dei dispari.
 */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>

int t, size = 0, average = 0, product = 0;

int main() {
  for (int i = 0; i < 5; i++) {
    printf("Dammi un numero: ");
    scanf(" %d", &t);

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
