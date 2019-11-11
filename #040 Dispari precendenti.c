/*
 * Jacopo Del Granchio
 * #040 12.11.2019
 *
 * Trova gli N numeri positivi, dispari minori di B.
 */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <locale.h>

int main() {
  setlocale(LC_ALL, "");

  int n, b;

  printf("Quanti numeri? ");
  scanf("%d", &n);

  printf("Da che numero? ");
  scanf("%d", &b);

  b = b - !(b % 2) - 2 * (b % 2);

  for (int i = 0; i < n && b > 0; i++) {
    printf("%d\t", b);
    b -= 2;

    if (i % 10 == 0) printf("\n");
  }

  printf("\nFatto.\n");

  // getchar();
  // system("pause");
  return 0;
}
