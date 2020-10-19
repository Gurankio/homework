/*
 * Jacopo Del Granchio
 * #034 06.11.2019
 *
 * Trove i divisori di un numero e controlla se è primo.
 */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <locale.h>

int main() {
  setlocale(LC_ALL, "");

  int n, c = 0;
  printf("%s ", "Dammi un numero: ");
  scanf("%d", &n);

  printf("%s\n", "I divisori sono: ");

  for (int i = 1; i <= n; i++) {
    if (n % i == 0) {
      printf("%d\t", i);
      c++;
    }
  }

  printf("\n");
  printf("%s%s%s\n", "Il numero", (c == 2 ? " " : " non "), "è primo.");

  // getchar();
  // system("pause");
  return 0;
}
