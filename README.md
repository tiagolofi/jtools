# tools
CLI escrito em Java para ser usado como caixa de ferramentas em um ambiente linux

jpackage \
  --type deb \
  --name tools \
  --input target \
  --main-jar tools-1.0.0-SNAPSHOT.jar \
  --main-class com.github.tiagolofi.run.Tools;

  git tag -a v1.0.0 -m "Release v1.0.0"
  git push origin v1.0.0