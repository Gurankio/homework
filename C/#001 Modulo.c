/*
 * Jacopo Del Granchio
 * #001
 *
 * Controlla se un numero è pari o dispari.
 */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>

int a;

int main() {
  printf("Dammi un numero.\n");
  scanf(" %d", &a);
  printf("Il numero è %s.\n", a % 2 == 0 ? "pari" : "dispari");

  getchar();
  return 0;
}
