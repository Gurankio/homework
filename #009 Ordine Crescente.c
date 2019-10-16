/*
 * Jacopo Del Granchio
 * #009
 *
 * Dati 3 numeri in input stamparli on ordine crescente.
 */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>

int main() {
  int a, b, c, min, mid, max;

  printf("Dammi tre numeri: ");
  scanf(" %d %d %d", &a, &b, &c);

  if (a < b) {
    min = a;
    max = b;
  } else {
    min = b;
    max = a;
  }

  if (c < min) {
    mid = min;
    min = c;
  } else if (c > max) {
    mid = max;
    max = c;
  } else mid = c;

  printf("Ordine Crescente: ");
  printf("%d, %d, %d\n", min, mid, max);

  //system("pause");
  return 0;
}
