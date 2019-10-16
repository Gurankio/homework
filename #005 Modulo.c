/*
 * Jacopo Del Granchio
 * #005
 *
 * Controlla se un numero è divisibile per il secondo e terzo input.
 */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>

int a, b, c;

int main() {
  printf("Dammi tre numeri: ");
  scanf(" %d %d %d", &a, &b, &c);

  if (a % (b * c) == 0) printf("'%d' è divisibile per '%d' e '%d'.\n", a, b, c);
  else printf("'%d' non è divisibile per '%d' e '%d'.\n", a, b, c);

  getchar();
  return 0;
}
