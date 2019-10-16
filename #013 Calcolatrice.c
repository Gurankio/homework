/*
 * Jacopo Del Granchio
 * #013 16.10.19
 *
 * Una smeplice calcolatrice che supporta le operazion: +, -, * e /.
 */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>

int main() {
  printf("Premi + per sommare.\n");
  printf("Premi - per sottrarre.\n");
  printf("Premi * per moltiplicare.\n");
  printf("Premi / per dividere.\n");
  printf("Premi # per uscire.\n");

  for (int i = 0; i < 3; ++i) {
    char scelta = 0;
    int a = 0, b = 0;

    printf("\nScelta:");
    scanf("%c", &scelta);

    switch (scelta) {
      case '+':
        printf("Dammi i due numeri: ");
        scanf("%d %d", &a, &b);
        printf("Il risultato e' %d\n", a + b);
        return 0;

      case '-':
        printf("Dammi i due numeri: ");
        scanf("%d %d", &a, &b);
        printf("Il risultato e' %d\n", a - b);
        return 0;

      case '*':
        printf("Dammi i due numeri: ");
        scanf("%d %d", &a, &b);
        printf("Il risultato e' %d\n", a * b);
        return 0;

      case '/':
        printf("Dammi i due numeri: ");
        scanf("%d %d", &a, &b);
        printf("Il risultato e' %d con resto %d\n", a / b, a % b);
        return 0;

      case '#':
        printf("Chiudo il programma.\n");
        return 0;

      default:
        printf("Scelta inesistente.\n");
    }
  }

  // getchar();
  // system("pause");
}
