/*
 * Jacopo Del Granchio
 * #029 06.11.2019
 *
 * Scompone un numero in unità, decine, centinaia e migliaia.
 */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <locale.h>

int main() {
  setlocale(LC_ALL, "");

  char a = '0', b = '0', c = '0', d = '0';

  do {
    if (a < 48 || a > 57 || b < 48 || b > 57 || c < 48 || c > 57 || d < 48 || d > 57) printf("Deve essere un numero.\n\n");

    printf("Dammi un numero: ");
    scanf("%c%c%c%c", &a, &b, &c, &d);
  } while (a < 48 || a > 57 || b < 48 || b > 57 || c < 48 || c > 57 || d < 48 || d > 57);

  printf("Unità: %c\n", d);
  printf("Decine: %c\n", c);
  printf("Centinaia: %c\n", b);
  printf("Migliaia: %c\n", a);

  // getchar();
  // system("pause");
  return 0;
}
