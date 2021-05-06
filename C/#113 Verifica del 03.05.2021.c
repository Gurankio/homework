#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/wait.h>
#include <sys/stat.h>
#include <sys/types.h>

int main(int argc, char const *argv[]) {
    for (int i=0; i < 4; i++) {
        pid_t pid;
        if (!(pid = fork())) {
            // child
            sleep(1);
            printf("  Sono il %d figlio, il mio pid e': %d. Il mio papi ha pid: %d\n", i, getpid(), getppid());
            exit(0);
        }
        printf("Sono il padre di %d , il mio pid e': %d. Il pid del mio figlio corrente e': %d\n", i, getpid(), pid);
        int status;
        waitpid(pid, &status, 0);
        printf("L'exit di mio figlio (%d) e': %d\n", pid, WEXITSTATUS(status));
    }
    return 0;
}

