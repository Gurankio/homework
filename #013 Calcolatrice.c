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
<<<<<<< HEAD
  char scelta;
  int a, b, risultato, fatto = 0;

=======
>>>>>>> f098054508b49ed6fdf0d3f5255c33b97dc3c073
  printf("Premi + per sommare.\n");
  printf("Premi - per sottrarre.\n");
  printf("Premi * per moltiplicare.\n");
  printf("Premi / per dividere.\n");
  printf("Premi # per uscire.\n");

<<<<<<< HEAD
  for (int i = 0; i < 3; i++) {
    printf("\nScelta: ");
    scanf(" %c", &scelta);

    if (scelta == '+' || scelta == '-' || scelta == '*' || scelta == '/') {
      printf("Dammi due numeri: ");
      scanf(" %d %d", &a, &b);

      switch (scelta) {
        case '+':
          risultato = a + b;
          fatto = 1;
          break;

        case '-':
          risultato = a - b;
          fatto = 1;
          break;

        case '*':
          risultato = a * b;
          fatto = 1;
          break;

        case '/':
          risultato = a / b;
          fatto = 1;
          break;
      }
    } else if (scelta == '#') {
      printf("Chiudo il programma\n");
      break;
    }

    if (fatto) {
      printf("Il risultato e' %d\n", risultato);
      break;
=======
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
>>>>>>> f098054508b49ed6fdf0d3f5255c33b97dc3c073
    }
  }

  // getchar();
  // system("pause");
}
