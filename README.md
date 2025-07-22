<center><div align="center">

<img height="100" src="src/main/resources/icon.png" width="100"/>

# MaFgLib

MaLiLib unofficial NeoForge port.

<img alt="neoforge" height="56" src="https://raw.githubusercontent.com/KessokuTeaTime/badges-extra/main/assets/cozy/supported/neoforge_vector.svg">

<a href="https://modrinth.com/mod/mafglib">
<img alt="modrinth" height="56" src="https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy/available/modrinth_vector.svg">
</a>
<a href="https://www.curseforge.com/minecraft/mc-mods/mafglib">
<img alt="curseforge" height="56" src="https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy/available/curseforge_vector.svg">
</a>

</div></center>

MaFgLib (or MaLiLib-Forge) is a library mod used by Masa's mods Forge port. It contains some common code previously
duplicated in most of the mods, such as multi-key capable keybinds, configuration GUIs etc.

[Original Repo Readme](Original-README.md)

## How to use in Minecraft
See [malilib wiki](https://github.com/maruohon/malilib/wiki)

## Development

This mod use modrinth maven.

```gradle
repositories {
    maven { url 'https://api.modrinth.com/maven' }
}

dependencies {
    modImplementation "maven.modrinth:mafglib:${mafglib_version}"
}
```

> Note: "${mafglib_version}" can be found in [Modrinth](https://modrinth.com/mod/mafglib)

## Compiling
- Clone the repository
- Open a command prompt/terminal to the repository directory
- run 'gradlew build'
- The built jar file will be in build/libs/

## Credits
- [maruohon/malilib](https://github.com/maruohon/malilib)
- [sakura-ryoko/malilib](https://github.com/sakura-ryoko/malilib)