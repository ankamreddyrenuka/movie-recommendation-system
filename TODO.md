# TODO

## Plan to fix build errors (#15)

1. Inspect `DestinationEntity` to confirm missing fields/methods. ✅
2. Update `DestinationEntity` to add `latitude` and `longitude` (and getters/setters) so `DataLoader` compiles.
3. Verify no other compilation errors exist by running `mvn clean package -DskipTests`.
4. If tests/build pass, ensure seeding still works with updated entity fields.


