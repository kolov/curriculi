# On manager
#
ufw allow 22/tcp
ufw allow 2376/tcp
ufw allow 2377/tcp
ufw allow 7946/tcp
ufw allow 7946/udp
ufw allow 4789/udp

ufw enable
ufw reload
# On worker

ufw allow 22/tcp
ufw allow 2376/tcp
ufw allow 7946/tcp
ufw allow 7946/udp
ufw allow 4789/udp

ufw enable
ufw reload
