#include <conio.h>
#include <stdio.h>

#ifdef _WIN32

int main() {
  unsigned char ch;

  // Avoid for ESC and CTRL+C
  while ((ch = getch()) != 27 && ch != 03) {
    if (ch == 224) {
      ch = getch();
      switch (ch) {
        case 72:
          printf("Arrow Up\n");
          break;

        case 75:
          printf("Arrow Left\n");
          break;

        case 77:
          printf("Arrow Right\n");
          break;

        case 80:
          printf("Arrow Down\n");
          break;
      }
    }

    // printf("%02d", ch);
  }

  return 0;
}

#endif /* ifdef _WIN32 */
