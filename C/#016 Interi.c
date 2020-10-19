/*
 * Jacopo Del Granchio
 * #016 19.10.2019
 *
 * Stampa i primi N numeri interi positivi.
 */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>

int main() {
  int n;

  printf("Quanti numeri interi? ");
  scanf("%d", &n);

  for (int i = 0; i < n; ++i) {
    printf("%d\t", i);

    if (i % 10 == 0) printf("\n");
  }

  printf("\nFatto.\n");

  // getchar();
  // system("pause");
  return 0;
}
