const express = require('express');
const { connectMongo } = require('./services/mongodb');
const { connectRabbitMQ } = require('./services/rabbitmq');
const commandesRoutes = require('./routes/commandes');

require('./consumer');

const app = express();

const PORT = process.env.PORT || 3001;
const INSTANCE = process.env.INSTANCE || 'Instance-?';

app.use(express.json());

/*
==================================================
🔹 ROUTES
==================================================
*/

// ✅ IMPORTANT : VRAI ROUTER
app.use('/commandes', commandesRoutes);

// Route health
app.get('/health', (req, res) => {
  res.json({
    status: 'OK',
    instance: INSTANCE
  });
});

// Route racine optionnelle
app.get('/', (req, res) => {
  res.json({
    message: "API Boutique active",
    instance: INSTANCE
  });
});

/*
==================================================
🔹 DÉMARRAGE SERVEUR
==================================================
*/

async function start() {
  try {
    await connectMongo();
    await connectRabbitMQ();

    app.listen(PORT, () => {
      console.log(`🚀 ${INSTANCE} lancée sur le port ${PORT}`);
    });

  } catch (err) {
    console.error("Erreur au démarrage :", err);
  }
}

start();