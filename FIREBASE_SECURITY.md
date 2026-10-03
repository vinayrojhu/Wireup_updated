# Firebase security deployment

The repository now contains Firestore and Storage rules, wired through `firebase.json`.

Before deploying, validate them against the Firebase Emulator Suite and the production
data schema. In particular, the current application stores comments and likes inside
tweet documents. The provided rules intentionally allow only a tweet owner to update a
tweet, so a future social-interaction redesign should move comments and reactions to
subcollections or trusted server-side functions before enabling public reactions.

Deploy only after review with:

`firebase deploy --only firestore:rules,storage`

Also enable Firebase App Check in the Firebase console for Authentication, Firestore,
and Storage, and restrict the Android API key to this application's package and signing
certificate in Google Cloud.
