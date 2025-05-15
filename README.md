# prog-5-ussd

## Description

`prog-5-ussd` est une application Java simulant un menu USSD interactif..

---

##  Objectif

Mettre en œuvre les meilleures pratiques de développement logiciel :

- Convention de nommage cohérente
- Linting automatique du code
- Intégration continue (CI) via GitHub Actions

---
---

## Conventions utilisées

### Nommage

| Élément        | Convention       |
|----------------|------------------|
| Classe         | upperCamelCase   |
| Méthode        | lowerCamelCase   |
| Variable       | lowerCamelCase   |
| Constante      | UPPER_SNAKE_CASE |

> Convention choisie : Java Standard + Google Java Style

---

## Linting

### Linter utilisé

- **Checkstyle** (standard Google Java Style)

### Vérification manuelle

```bash
./mvn checkstyle:check
```

## Test

### Prérequis
- JDK 17 ou supérieur installé
- Maven
### Étapes pour compiler et exécuter
```bash
 mvn compile
```

```bash
 mvn exec:java -Dexec.mainClass="Main"
```