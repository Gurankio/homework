/*
 * Jacopo Del Granchio
 * #006
 *
 * Controlla se un numero è divisibile per il secondo input ma non per il terzo.
 */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>

int a, b, c;

int main() {
  printf("Dammi tre numeri: ");
  scanf(" %d %d %d", &a, &b, &c);

  if (a % b == 0 && a % c != 0) printf("'%d' è divisibile per '%d' ma non per '%d'.\n", a, b, c);
  else printf("'%d' non è divisibile per '%d' e/o è divisibile per '%d'.\n", a, b, c);

  getchar();
  return 0;
}
