/*
 * Jacopo Del Granchio
 * #007
 *
 * Controlla se la a alla n è pari o dispari.
 */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>

int a, b;

int main() {
  printf("Dammi due numeri: ");
  scanf(" %d %d", &a, &b);

  if (b != 0) {
    if (a % 2 == 0) printf("'%d' alla '%d' è pari.\n", a, b);
    else printf("'%d' alla '%d' è dispari.\n", a, b);
  } else printf("'%d' alla '%d' è dispari.\n", a, b);

  getchar();
  return 0;
}
