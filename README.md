# JTools
CLI escrito em Java para ser usado como caixa de ferramentas em um ambiente linux

# Compilação

```bash
jpackage \
  --type deb \
  --name jtools \
  --input target \
  --main-jar jtools-1.0.0-SNAPSHOT.jar \
  --main-class com.github.tiagolofi.comandos.JTools;
```

# Publicação da tag

```bash
git tag -a v1.0.0 -m "Release v1.0.0"
git push origin v1.0.0
```