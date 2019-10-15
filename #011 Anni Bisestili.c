/*
 * Jacopo Del Granchio
 * #011 15.10.2019
 * Controlla se un anno è bisestile.
 */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>

int main() {
  int n;

  printf("Dammi un anno: ");
  scanf(" %d", &n);

  if (n % 100 == 0) {
    if (n % 400 == 0) printf("Bisestile.\n");
    else printf("Non è bisestile.\n");
  } else {
    if (n % 4 == 0) printf("Bisestile.\n");
    else printf("Non è bisestile.\n");
  }

  getchar();
  // system("pause");
  return 0;
}
