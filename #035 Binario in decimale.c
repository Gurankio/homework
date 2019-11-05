/*
 * Jacopo Del Granchio
 * #035 06.11.2019
 *
 * Converte un numero binario in un numero decimale.
 */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <locale.h>

int main() {
  setlocale(LC_ALL, "");

  int n, t, r = 0;

  printf("%s ", "Quanti bit?");
  scanf("%d", &n);

  //int s[n];

  for (int i = 0; i < n; i++) {
    do {
      printf("%s%d%s", "Dammi il ", i + 1, " bit: ");
      scanf("%d", &t);
    } while (t != 0 && t != 1);

    r += t * pow(2, i);
    // s[i] = t;
  }

  // printf("%s", "Binario: ");
  // for (int i = n - 1; i >= 0; i--) printf("%d", s[i]);
  // printf("\n");

  printf("%s%d\n", "Decimale: ", r);

  // getchar();
  // system("pause");
  return 0;
}
