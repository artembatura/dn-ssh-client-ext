<?php

namespace php\ssh\client;

/**
 * Class SSHClient
 * @package php\ssh\client
 */
final class SSHClient
{
    private function __construct()
    {
    }

    /**
     * @param string $host
     * @param int $port
     * @param string $user
     * @param string $pass
     * @return Connection
     */
    public static function connectWithPassword($host, $port, $user, $pass) {
    }
}