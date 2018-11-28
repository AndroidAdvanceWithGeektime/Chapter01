#include "dl_iterate_phdr.h"
#include <stdbool.h>
#include <dlfcn.h>
#include <fcntl.h>
#include <elf.h>
#include "os-linux.h"
#include "libunwind_i.h"

#ifndef IS_ELF
#define IS_ELF(ehdr) ((ehdr).e_ident[EI_MAG0] == ELFMAG0 && \
                      (ehdr).e_ident[EI_MAG1] == ELFMAG1 && \
                      (ehdr).e_ident[EI_MAG2] == ELFMAG2 && \
                      (ehdr).e_ident[EI_MAG3] == ELFMAG3)
#endif

#if defined(__arm__) && __ANDROID_API__ < 21

// Function types: callback and function itself.
typedef int (*dl_iterate_phdr_callback) (struct dl_phdr_info *info, size_t size, void *data);
typedef int (*dl_iterate_phdr_signature)(dl_iterate_phdr_callback callback, void *data);

int dl_iterate_phdr(int (*callback)(struct dl_phdr_info*, size_t, void*), void *data)
{
    dl_iterate_phdr_signature dl_iterate_phdr_address = (dl_iterate_phdr_signature) dlsym(RTLD_NEXT, "dl_iterate_phdr");
    if (dl_iterate_phdr_address) {
        return dl_iterate_phdr_address(callback, data);
    }

    int rc = 0;
    struct map_iterator mi;
    unsigned long start, end, offset, flags;

    if (maps_init (&mi, getpid()) < 0)
        return -1;

    while (maps_next (&mi, &start, &end, &offset, &flags))
    {
        Elf_W(Ehdr) *ehdr = (Elf_W(Ehdr) *) start;
        const unsigned long prot_rx = PROT_READ | PROT_EXEC;

        if (mi.path[0] != '\0' && (flags & prot_rx) == prot_rx && IS_ELF (*ehdr))
        {
            Elf_W(Phdr) *phdr = (Elf_W(Phdr) *) (start + ehdr->e_phoff);
            struct dl_phdr_info info;

            info.dlpi_addr = start;
            info.dlpi_name = mi.path;
            info.dlpi_phdr = phdr;
            info.dlpi_phnum = ehdr->e_phnum;
            rc = callback (&info, sizeof (info), data);
        }
    }

    maps_close (&mi);

    return rc;
}

#endif //defined(__arm__) && __ANDROID_API__ < 21